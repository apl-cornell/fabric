#!/usr/bin/perl -np
s/\bK\b/long/;
s/ConcurrentMap<long, /ConcurrentLongKeyMap</;
s/ConcurrentHashMap<long, /ConcurrentLongKeyHashMap</;
s/WarrantyIssuer/LongKeyWarrantyIssuer/;
s/LongKeyWarrantyIssuer<long, /LongKeyWarrantyIssuer</;
s/import java.util.concurrent.ConcurrentMap/import fabric.common.util.ConcurrentLongKeyMap/;
s/import java.util.concurrent.ConcurrentHashMap/import fabric.common.util.ConcurrentLongKeyHashMap/;
s/key instanceof Number .. ..Number. key..longValue.. == 0/key == 0/;
