# Discord.java

## Mac Java Environment
~/.zshenv
```bash
/usr/libexec/java_home -V
/usr/libexec/java_home -v17.0.2
export JAVA_HOME=$(/usr/libexec/java_home)
```

## Docker Setup
[Docker Desktop Download](https://www.docker.com/)
[Linux docker-23.0.1.tgz](https://download.docker.com/linux/static/stable/x86_64/)
[Linux docker-compose-linux-x86_64](https://github.com/docker/compose/releases)
```bash
tar zxvf docker-23.0.1.tgz -C /usr/bin/ --strip-components=1
cp docker.service /etc/systemd/system/docker.service
systemctl daemon-reload
systemctl enable docker.service
systemctl start docker
sudo usermod -aG docker $USER #add current user to docker group
newgrp docker #no need re-login
cp docker-compose /usr/bin/
chmod +x /usr/bin/docker-compose
docker-compose up
```
