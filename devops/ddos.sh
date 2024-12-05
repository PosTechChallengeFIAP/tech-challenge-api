#!/bin/bash
for i in $(seq 1 10000); do
    curl 192.168.49.2:30808/client
    echo $i
    sleep $1
done