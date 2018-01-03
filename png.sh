#!/usr/bin/env bash

[ -x pngquant ] && echo "E: pngquant not found" >&2 && exit 1

start=$(date +%s)

sizeIn=0
sizeOut=0
while read dir ; do
    sizeIn=$(($sizeIn + $(du -s "$dir" | awk '{print $1}')))
    while read file ; do
        echo "$file"
        cp "$file" "/tmp/png"
        pngquant -f "/tmp/png" ; [ $? -ne 0 ] && echo "ERROR" >&2 && exit 1 ; mv "/tmp/png-fs8.png" "$file"
    done < <(find "$dir" -_type f -name "*.png" -not -name "*.9.png" | grep -v "build")
    sizeOut=$(($sizeOut + $(du -s "$dir" | awk '{print $1}')))
done < <(find "." -_type d -name "res" | grep -v "build")

ratio=$(echo "100 - (($sizeOut * 100) / $sizeIn)" | bc -q)
end=$(date +%s)
time=$(echo "$end - $start" | bc -q)
echo "+--------+"
echo "| INPUT  | ${sizeIn}B"
echo "| OUTPUT | ${sizeOut}B (-$ratio%)"
echo "| TIME   | ${time}s"
echo "+--------+"

# EOF
