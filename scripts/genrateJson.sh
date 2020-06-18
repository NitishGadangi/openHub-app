#!/bin/bash
post=true
releaseDir="app/build/outputs/apk/release/"
version=$(cat $releaseDir"output.json" | jq  -r '.[] | .apkData | .versionName')

VAR=$(git log --oneline  -n7 | cut -c 1-$COLUMNS | awk '{ { for (i=2; i<NF; i++) printf $i " "; print $NF } }' | jq -R -s -c  'split("\n")' | jq '[ .[] | if length > 0 then . else empty end ]')
apkfile=./$releaseDir"app-release.apk"
apkUrl=$(curl --upload-file $apkfile https://transfer.sh/app-release-$version.apk)
echo $apkUrl
echo $version
if $post ; then
    echo $(curl --location --request GET 'https://api.vidflix.net/v2/api/home/updt_auto_dsdDADADFF79JHE.php' \
--header 'Content-Type: application/json' \
--data-raw '{
        "version": '\"$version\"',
        "apkUrl": '\"$apkUrl\"',
        "changelog": '"$VAR"'
      }')
fi