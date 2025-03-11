#!/bin/bash

# .env file expected to be 1 level above the folder
# where this script is running,
# e.g. /.env and /database/db_setup.sh
source ../.env

export PGPASSWORD=$DATABASE_PASSWORD
psql -U $DATABASE_USER -d $DATABASE_CONNECTION_STR

# Run your DB setup script from the psql shell
#   \i /path/to/your/sql-script.sql
# example,
#   \i ./m2_final_project.sql

# Check users table created
# SELECT * FROM users;

# \q to exit psql shell