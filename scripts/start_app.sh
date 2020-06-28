#!/bin/bash
set -e
cat /opt/employees.service > /etc/systemd/system/employees.service
systemctl daemon-reload
systemctl start employees.service
