package factionai.java.discord.spring;

import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import discord4j.common.ReactorResources;
import discord4j.common.ReactorResources.Builder;
import discord4j.core.DiscordClientBuilder;
import discord4j.core.GatewayDiscordClient;
import discord4j.core.object.presence.ClientActivity;
import discord4j.core.object.presence.ClientPresence;
import discord4j.gateway.GatewayReactorResources;
import discord4j.rest.RestClient;
import lombok.extern.slf4j.Slf4j;
import reactor.netty.http.client.HttpClient;
import reactor.netty.transport.ProxyProvider;

@Component
@Slf4j
public class DiscordConfig {
  private final Environment env;

  public DiscordConfig(final Environment env) {
    this.env = env;
  }

  @Bean
  public GatewayDiscordClient gatewayDiscordClient() {
    HttpClient http, gateway;
    String host = env.getProperty("bot.proxy.host");
    if (host == null) {
      http = HttpClient.create().compress(true).followRedirect(true)
          .secure();
    } else {
      http = HttpClient.create().compress(true).followRedirect(true)
          .proxy(spec -> spec.type(ProxyProvider.Proxy.SOCKS5)
              .host(host)
              .port(Integer.parseInt(env.getProperty("bot.proxy.port"))))
          .secure();
    }

    String block = http.get()
        .uri("https://icanhazip.com")
        .responseContent()
        .aggregate()
        .asString()
        .block();
    log.info("My proxy ip = {}", block);

    ReactorResources build = ReactorResources.builder().httpClient(http).build();
    return DiscordClientBuilder.create(env.getRequiredProperty("bot.token"))
        .setReactorResources(build)
        .build()
        .gateway()
        .setGatewayReactorResources(reactorResources -> new GatewayReactorResources(build))
        .setInitialPresence(ignore -> ClientPresence.online(ClientActivity.listening("to /commands")))
        .login()
        .block();
  }

  @Bean
  public RestClient discordRestClient(GatewayDiscordClient client) {
    return client.getRestClient();
  }
}
