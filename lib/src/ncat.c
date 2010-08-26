/**
 * A quick & dirty hack for dumping a file to a network port.
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

int main(int argc, char** argv) {
  int sock, port;
  char* dest_addr = NULL;
  FILE* input;
  int nfds;

  // Sanity-check arguments.
  if (argc != 3 && argc != 4) {
    fprintf(stderr, "Usage: %s <file> [dest-addr] <port>\n", argv[0]);
    exit(-1);
  }

  // Parse args.
  input = fopen(argv[1], "r");
  if (argc == 3) {
    port = atoi(argv[2]);
  } else {
    dest_addr = argv[2];
    port = atoi(argv[3]);
  }

  if (dest_addr != NULL) {
    // Connect to server.
    sock = connect_to(dest_addr, port);
  } else {
    // Create and bind a socket for listening.
    int listen_sock = socket(PF_INET, SOCK_STREAM, 0);
    struct sockaddr_in addr;
    int addr_len = sizeof(addr);

    addr.sin_family = AF_INET;
    addr.sin_addr.s_addr = INADDR_ANY;
    addr.sin_port = htons(port);
    if (bind(listen_sock, (struct sockaddr*) &addr, addr_len)) {
      fprintf(stderr, "bind returned %d\n", errno);
      exit(-1);
    }

    // Listen for a connection.
    if (listen(listen_sock, 1)) {
      fprintf(stderr, "listen returned %d\n", errno);
      exit(-1);
    }

    // Accept the connection.
    sock = accept(listen_sock, (struct sockaddr*) &addr, &addr_len);
  }

  // Send data.
  nfds = sock+1;
  while (1) {
    int retval;
    fd_set rfds;
    fd_set wfds;

    char buf[1024*8];
    int written = 0;
    int size = 0;

    FD_SET(sock, &rfds);
    FD_SET(sock, &wfds);

    retval = select(nfds, &rfds, &wfds, NULL, NULL);
    if (retval == -1) {
      fprintf(stderr, "Error from select: %d\n", errno);
      exit(-1);
    }

    if (retval == 0) continue;

    if (FD_ISSET(sock, &rfds)) {
      char read_buf[1024*8];
      printf("Read %d\n", read(sock, read_buf, 1024*8));
    }

    if (FD_ISSET(sock, &wfds)) {
      if (written == size) {
	size = fread(buf, 1, 1024*8, input);
	written = 0;

	if (size <= 0) exit(-1);
      }

      retval = write(sock, buf+written, size-written);
      if (retval == -1) {
	fprintf(stderr, "Error sending data: %d\n", errno);
	exit(-1);
      }

      written += retval;
    }
  }
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
    exit(-1);
  }

  // Resolve the hostname to an IP address.
  host_ptr = gethostbyname(addr);
  if (host_ptr == NULL) {
    host_ptr = gethostbyaddr(addr, strlen(addr),
	AF_INET);
    if (host_ptr == NULL) {
      fprintf(stderr, "Error resolving host: %s\n", addr);
      exit(-1);
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
    exit(-1);
  }

  return sock;
}


