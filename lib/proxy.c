/**
 * A quick & dirty hack for counting the number of bytes transferred across a
 * TCP session.  This program sits between two TCP end-points, shuttling data
 * back and forth between them.  Upon exit, the program prints the number of
 * bytes sent and received from the client's point of view.
 */
#include <errno.h>
#include <netdb.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>

#include <arpa/inet.h>
#include <sys/types.h>
#include <sys/select.h>
#include <sys/socket.h>

int connect_to(char* addr, short port);
void tunnel(int client, int server);
void die();

// Counters from client's point of view.
int sent, received;

int main(int argc, char** argv) {
  int client_sock, dest_sock, listen_sock, local_port, dest_port;
  char* dest_addr;

  struct sockaddr_in addr;
  int addr_len;

  // Sanity-check arguments.
  if (argc != 4) {
    fprintf(stderr, "Usage: %s <listen-port> <dest-addr> <dest-port>\n");
    die();
  }

  // Parse args.
  local_port = atoi(argv[1]);
  dest_addr = argv[2];
  dest_port = atoi(argv[3]);

  // Create and bind a socket for listening.
  listen_sock = socket(PF_INET, SOCK_STREAM, 0);
  addr.sin_family = AF_INET;
  addr.sin_addr.s_addr = INADDR_ANY;
  addr.sin_port = htons(local_port);
  addr_len = sizeof(addr);
  if (bind(listen_sock, (struct sockaddr*) &addr, addr_len)) {
    fprintf(stderr, "bind returned %d\n", errno);
    die();
  }

  // Listen for a connection.
  if (listen(listen_sock, 1)) {
    fprintf(stderr, "listen returned %d\n", errno);
    die();
  }

  // Accept the connection.
  client_sock = accept(listen_sock, (struct sockaddr*) &addr, &addr_len);

  // Connect to server.
  dest_sock = connect_to(dest_addr, dest_port);

  // Tunner traffic.
  tunnel(client_sock, dest_sock);
}

/**
 * Connects to the given hostname:port combination, returning the resulting
 * socket.
 */
int connect_to(char* addr, short port) {
  // This will contain the destination IP address and port number.  Used by
  // connect().
  struct sockaddr_in dest_addr = { 0 };

  // A struct for holding the result of looking up a hostname.
  struct hostent* host_ptr;

  // Create the socket.
  int sock = socket(PF_INET, SOCK_STREAM, IPPROTO_TCP);
  if (sock == -1) {
    fprintf(stderr, "Error creating socket.  (errno=%d)\n", errno);
    die();
  }

  // Resolve the hostname to an IP address.
  host_ptr = gethostbyname(addr);
  if (host_ptr == NULL) {
    host_ptr = gethostbyaddr(addr, strlen(addr),
	AF_INET);
    if (host_ptr == NULL) {
      fprintf(stderr, "Error resolving host: %s\n", addr);
      die();
    }
  }

  // Set up the destination socket address struct.
  dest_addr.sin_family = AF_INET;
  dest_addr.sin_port = htons(port);
  memcpy(&dest_addr.sin_addr, host_ptr->h_addr, host_ptr->h_length);

  // Connect the socket to the destination.
  if (connect(sock, (struct sockaddr*) &dest_addr, sizeof(dest_addr))
	== -1) {
    fprintf(stderr, "Error connecting to %s:%d.  (errno=%d)\n",
	addr, port, errno);

    switch (errno) {
    case ECONNREFUSED:
      fprintf(stderr, "  Connection refused.\n");
      break;

    case ENETUNREACH:
      fprintf(stderr, "  Network is unreachable.\n");
      break;

    case ETIMEDOUT:
      fprintf(stderr, "  Connection timed out.\n");
      break;
    }
    die();
  }

  return sock;
}

void transfer(int src, int dst, int* counter);

/**
 * Shuttles data between two given sockets.
 */
void tunnel(int client, int server) {
  int nfds = client+1;
  if (server > client) nfds = server+1;
  while (1) {
    int retval;
    fd_set fds;
    FD_SET(client, &fds);
    FD_SET(server, &fds);

    retval = select(nfds, &fds, NULL, NULL, NULL);
    if (retval == -1) {
      fprintf(stderr, "Error from select: %d\n", errno);
      die();
    }

    if (retval > 0) {
      if (FD_ISSET(client, &fds)) transfer(client, server, &sent);
      if (FD_ISSET(server, &fds)) transfer(server, client, &received);
    }
  }
}

/**
 * Reads from src socket, writes to dst socket, updates given counter.
 */
void transfer(int src, int dst, int* counter) {
  char buf[1024];
  int size = read(src, buf, 1024);
  int written = 0;

  if (size == -1) {
    fprintf(stderr, "Error reading from network: %d\n", errno);
    die();
  }

  *counter = *counter + size;

  while (written < size) {
    int retval = write(dst, buf+written, size-written);
    if (retval == -1) {
      fprintf(stderr, "Error sending data: %d\n", errno);
      die();
    }

    written += retval;
  }
}

void die() {
  printf("Client sent %d bytes.\n", sent);
  printf("Client received %d bytes.\n", received);
  exit(-1);
}

