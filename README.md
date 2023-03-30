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

## Discord Setup
* [Get a Discord Bot Token](https://discordgsm.com/guide/how-to-get-a-discord-bot-token)
* [Get a Guild ID](https://en.wikipedia.org/wiki/Template:Discord_Channel#:~:text=Getting%20Channel%2FGuild%20ID,to%20get%20the%20guild%20ID.)
* [Monitor Bot](https://discordgsm.com/guide/deploy-with-docker)
* [Enable Privileged Gateway Intents](https://discord.com/developers/applications/1087974448440823818/bot)
* [Inviting Your Bot](https://discord.com/oauth2/authorize?client_id={CLIENT_ID_HERE}&permissions=0&scope=bot%20applications.commands)
```bash
# -v discord-game-server-monitor-data:/usr/src/app/data \
# --restart=always \
export https_proxy=socks5://localhost:1080;export http_proxy=socks5://localhost:1080;export all_proxy=socks5://localhost:1080
docker run --rm \
  --name discord-game-server-monitor \
  -e HTTPS_PROXY=socks5://host.docker.internal:1080 \
  -e  HTTP_PROXY=socks5://host.docker.internal:1080 \
  -e APP_TOKEN=MTA4Nzk3NDQ0ODQ0MDgyMzgxOA.GUKxV3.BKfzstl3L5qQX38zvPx8KEezhvFGH4afroNhLs \
  -e WHITELIST_GUILDS=1087970593342758914 \
  discordgsm/discord-game-server-monitor:latest python main.py
# Can't run due to python aiohttp client proxy issue
```
