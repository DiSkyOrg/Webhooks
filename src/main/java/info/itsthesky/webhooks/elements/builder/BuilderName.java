package info.itsthesky.webhooks.elements.builder;

import ch.njol.skript.classes.Changer;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import club.minnced.discord.webhook.send.WebhookMessageBuilder;
import info.itsthesky.webhooks.elements.WebhookExpression;
import org.jetbrains.annotations.NotNull;

@Name("Builder Name")
@Description("Change the name of any webhook message builder.")
@Examples("set webhook name of builder to \"username\"")
public class BuilderName extends WebhookExpression<String> {

	static {
		register(BuilderName.class, String.class, "name");
	}

	@Override
	public void change(Changer.ChangeMode mode, String value, WebhookMessageBuilder builder) {
		if (mode == Changer.ChangeMode.SET)
			builder.setUsername(value);
	}

	@Override
	public @NotNull String getProperty() {
		return "name";
	}
}
