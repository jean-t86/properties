#!/bin/bash

# Install 1Password CLI if not already installed
if ! command -v op &> /dev/null; then
    echo "1Password CLI is not installed. Please install it from https://1password.com/downloads/command-line/"
    exit 1
fi

export OP_SERVICE_ACCOUNT_TOKEN=TOKEN_PLACEHOLDER

# Retrieve the secrets from 1Password vault
keystorePassword=$(op read "op://Sentia/Sentia Android Keystore/password")
keyAlias=$(op read "op://Sentia/Sentia APK Release Signing Key/username")
keyPassword=$(op read "op://Sentia/Sentia APK Release Signing Key/password")

# Check if any of the secrets were not retrieved successfully
if [ -z "$keystorePassword" ] || [ -z "$keyAlias" ] || [ -z "$keyPassword" ]; then
    echo "Failed to retrieve one or more secrets from 1Password."
    exit 1
fi

# Generate the config.properties file
echo "keystorePassword=$keystorePassword" > config.properties
echo "keyAlias=$keyAlias" >> config.properties
echo "keyPassword=$keyPassword" >> config.properties

echo "Config file 'config.properties' generated successfully."
