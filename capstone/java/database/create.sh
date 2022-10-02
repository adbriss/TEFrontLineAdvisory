#!/bin/bash
export PGPASSWORD='postgres1'
BASEDIR=$(dirname $0)
DATABASE=project_schedule_db
psql -U postgres -f "$BASEDIR/dropdb.sql" &&
createdb -U postgres $DATABASE &&
psql -U postgres -d $DATABASE -f "$BASEDIR/schema.sql" &&
psql -U postgres -d $DATABASE -f "$BASEDIR/user.sql" &&
psql -U postgres -d $DATABASE -f "$BASEDIR/data/userData.sql" &&
psql -U postgres -d $DATABASE -f "$BASEDIR/data/phaseItemsData.sql" &&
psql -U postgres -d $DATABASE -f "$BASEDIR/data/contractData.sql" &&
psql -U postgres -d $DATABASE -f "$BASEDIR/data/projectData.sql"&&
psql -U postgres -d $DATABASE -f "$BASEDIR/data/scheduleData.sql"&&
psql -U postgres -d $DATABASE -f "$BASEDIR/views.sql"