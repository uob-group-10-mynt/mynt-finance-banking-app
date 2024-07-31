#!/bin/bash

# Database credentials
DB_HOST="users.c3g02ksos4tb.eu-west-2.rds.amazonaws.com"
DB_PORT="5432"
DB_NAME="postgres"
DB_USER="backend_user"
DB_PASS="iambackenduser"

# Output directory and file
OUTPUT_DIR="src/test/resources"
OUTPUT_FILE="$OUTPUT_DIR/schema.sql"

# Create the output directory if it doesn't exist
mkdir -p $OUTPUT_DIR

# Export the schema
PGPASSWORD=$DB_PASS pg_dump --host=$DB_HOST --port=$DB_PORT --username=$DB_USER --no-owner --schema-only $DB_NAME > $OUTPUT_FILE

# Check if the extraction was successful
if [ $? -eq 0 ]; then
    echo "Schema extraction successful. Output file: $OUTPUT_FILE"
else
    echo "Schema extraction failed."
    exit 1
fi
