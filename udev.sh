#!/usr/bin/env bash

[ $(id -u) -ne 0 ] && echo "E: root privileges required" >&2 && exit 1

mkdir -p "/etc/udev/rules.d"
file="/etc/udev/rules.d/51-android.rules"
rm -f "$file"

echo "I: Scanning..."

c=$(lsusb | grep Nexus | wc -l)
[ $c -eq 0 ] && echo "E: no Nexus device(s) detected" >&2 && exit 1

while read line ; do
    # Bus 002 Device 003: ID 18d1:4ee2 Google Inc. Nexus 4 (debug)
    device=$(echo $line | cut -d' ' -f7-)
    vendor=$(echo $line | cut -d' ' -f6 | cut -d':' -f1)
    product=$(echo $line | cut -d' ' -f6 | cut -d':' -f2)
    echo "Adding '$device' ($vendor:$product) as '0666' to 'plugdev' group"
    echo "# $device" >> "$file"
    echo "SUBSYSTEM==\"usb\", ATTR{idVendor}==\"$vendor\", ATTR{idProduct}==\"$product\", MODE=\"0666\", GROUP=\"plugdev\", SYMLINK+=\"android%n\"" >> "$file"
done < <(lsusb | grep Nexus)

service udev restart > /dev/null

echo "I: unplug and replug device(s)"

# EOF
