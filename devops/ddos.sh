#!/bin/bash
for i in $(seq 1 10000); do
    curl localhost:30808/client
    echo $i
    sleep $1
done
