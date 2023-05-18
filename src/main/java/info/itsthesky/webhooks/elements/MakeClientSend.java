package info.itsthesky.webhooks.elements;

import ch.njol.skript.Skript;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Name;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.Variable;
import ch.njol.util.Kleenean;
import club.minnced.discord.webhook.WebhookClient;
import club.minnced.discord.webhook.send.WebhookMessageBuilder;
import info.itsthesky.disky.api.generator.Module;
import info.itsthesky.disky.api.skript.WaiterEffect;
import info.itsthesky.webhooks.Webhooks;
import org.bukkit.event.Event;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Name("Make Client Send Builder")
@Description({"Make a specific webhook client send a webhook message builder.",
"Don't forget to register the client before, then use the registered name on this effect.",
"The builder have to be made through a webhook message builder section! This effect should also be inside the section.",
"Because of Discord's limitation, you can only get back the message's ID. You have to retrieve it through DiSky's retrieve message effect."})
@Module("Webhooks")
public class MakeClientSend extends WaiterEffect<String> {

	static {
		Skript.registerEffect(
				MakeClientSend.class,
				"make [the] [webhook] client %string% send [the] [message] %webhookmessage% [and store (it|the message id) in %-string%]"
		);
	}

	private Expression<String> exprName;
	private Expression<WebhookMessageBuilder> exprBuilder;

	@Override
	public boolean initEffect(Expression[] expressions, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult) {
		exprName = (Expression<String>) expressions[0];
		exprBuilder = (Expression<WebhookMessageBuilder>) expressions[1];
		setChangedVariable((Variable<String>) expressions[2]);
		return true;
	}

	@Override
	public void runEffect(Event e) {
		final String name = parseSingle(exprName, e, null);
		final WebhookMessageBuilder builder = parseSingle(exprBuilder, e, null);
		if (anyNull(this, name, builder))
			return;

		final WebhookClient client = Webhooks.getManager().getClient(name);
		if (client == null) {
			Skript.error("The webhook client '" + name + "' is not registered!");
			restart();
			return;
		}

		client.send(builder.build()).thenAccept(readonlyMessage -> restart(String.valueOf(readonlyMessage.getId())));
	}

	@Override
	public @NotNull String toString(@Nullable Event e, boolean debug) {
		return "make webhook client " + exprName.toString(e, debug) + " send message " + exprBuilder.toString(e, debug) +
				(getChangedVariable() == null ? "" : " and store the message id in " + getChangedVariable().toString(e, debug));
	}
}
