#!/bin/bash
grep '172.16.21.86'  localhost_access_log.2015-07-17.txt | awk '{print $1}'