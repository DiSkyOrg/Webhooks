package info.itsthesky.webhooks.elements.builder;

import ch.njol.skript.classes.Changer;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import club.minnced.discord.webhook.send.WebhookMessageBuilder;
import info.itsthesky.webhooks.elements.WebhookExpression;
import org.jetbrains.annotations.NotNull;

@Name("Builder Avatar")
@Description("Change the avatar of any webhook message builder.")
@Examples("set webhook avatar of builder to \"avatar url\"")
public class BuilderAvatar extends WebhookExpression<String> {

	static {
		register(BuilderAvatar.class, String.class, "avatar");
	}

	@Override
	public void change(Changer.ChangeMode mode, String value, WebhookMessageBuilder builder) {
		if (mode == Changer.ChangeMode.SET)
			builder.setAvatarUrl(value);
	}

	@Override
	public @NotNull String getProperty() {
		return "avatar";
	}
}
