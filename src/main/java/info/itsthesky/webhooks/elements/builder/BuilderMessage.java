package info.itsthesky.webhooks.elements.builder;

import ch.njol.skript.classes.Changer;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import club.minnced.discord.webhook.send.WebhookMessageBuilder;
import info.itsthesky.webhooks.elements.WebhookExpression;
import org.jetbrains.annotations.NotNull;

@Name("Builder Content")
@Description("Change the content of any webhook message builder.")
@Examples("set webhook content of builder to \"Here's some text\"")
public class BuilderMessage extends WebhookExpression<String> {

	static {
		register(BuilderMessage.class, String.class, "content");
	}

	@Override
	public void change(Changer.ChangeMode mode, String value, WebhookMessageBuilder builder) {
		if (mode == Changer.ChangeMode.SET)
			builder.setContent(value);
	}

	@Override
	public @NotNull String getProperty() {
		return "message";
	}
}
