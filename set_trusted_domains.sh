#!/bin/bash
CONFIG_PATH=/var/www/html/config/config.php
if ! grep -q "'trusted_domains' =>" $CONFIG_PATH; then
    echo "Setting trusted domain in config.php"
    sed -i "s/0 => 'localhost'/0 => 'localhost', 1 => 'nextcloud'/" $CONFIG_PATH
else
    echo "Trusted domain already set"
fi