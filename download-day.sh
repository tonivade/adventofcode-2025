#!/bin/bash

# import year and session_id from .env file
source .env

next_day="${1?required day}"

mkdir -p input

curl "https://adventofcode.com/${year}/day/${next_day}/input" \
  --cookie "session=$session_id" > "input/day${next_day}.txt"
