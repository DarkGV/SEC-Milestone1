#!/bin/bash

current_branch=$(git rev-parse --abbrev-ref HEAD)
files_to_push="$*"

git pull origin $current_branch
git add "$files_to_push"

echo "Enter message you wish to attach to the commit:"
read message

git commit -m "\"$message\""
git push origin "$current_branch"

echo "The files \"$*\" have been pushed to the branch \"$current_branch\""