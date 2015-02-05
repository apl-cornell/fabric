#!/usr/bin/perl -np
s/\bK\b/long/g;
s/AccessMetrics/LongKeyAccessMetrics/g;
#s/Cache/LongKeyCache/g; // Trying to keep the guava cache for now.
s/Cache<long/Cache<Long/g;
s/ConcurrentMap<long, /ConcurrentLongKeyMap</g;
s/ConcurrentHashMap<long, /ConcurrentLongKeyHashMap</g;
s/LeaseIssuer/LongKeyLeaseIssuer/g;
s/WarrantyIssuer/LongKeyWarrantyIssuer/g;
s/LongKeyAccessMetrics<long>/LongKeyAccessMetrics/g;
s/LongKeyCache<long, /LongKeyCache</g;
s/LongKeyLeaseIssuer<long, /LongKeyLeaseIssuer</g;
s/LongKeyWarrantyIssuer<long, /LongKeyWarrantyIssuer</g;
s/import java.util.concurrent.ConcurrentMap/import fabric.common.util.ConcurrentLongKeyMap/g;
s/import java.util.concurrent.ConcurrentHashMap/import fabric.common.util.ConcurrentLongKeyHashMap/g;
s/key instanceof Number .. ..Number. key..longValue.. == 0/key == 0/g;
