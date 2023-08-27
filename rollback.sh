docker run --rm -v caravelletravels_db-data:/volume -v /home/almaz:/backup alpine sh -c "rm -rf /volume/* /volume/..?* /volume/.[!.]* ; tar -C /volume/ -xjf /backup/db-backup.tar.bz2"
docker run --rm -v caravelletravels_app-logs:/volume -v /home/almaz:/backup alpine sh -c "rm -rf /volume/* /volume/..?* /volume/.[!.]* ; tar -C /volume/ -xjf /backup/logs-backup.tar.bz2"
