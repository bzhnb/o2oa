#!/bin/bash
# Copyright (c) http://www.o2oa.net/
current_dir="$(
    cd "$(dirname "$0")"
    pwd
)"
cd ${current_dir}
${current_dir}/stop_mips.sh
${current_dir}/start_mips.sh
