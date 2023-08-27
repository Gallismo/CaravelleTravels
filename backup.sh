docker run --rm -v caravelletravels_db-data:/volume -v /home/almaz:/backup alpine tar -cjf /backup/db-backup.tar.bz2 -C /volume ./
docker run --rm -v caravelletravels_app-logs:/volume -v /home/almaz:/backup alpine tar -cjf /backup/logs-backup.tar.bz2 -C /volume ./
