package info.itsthesky.webhooks.elements.builder;

import ch.njol.skript.classes.Changer;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import club.minnced.discord.webhook.send.WebhookMessageBuilder;
import info.itsthesky.disky.api.generator.Module;
import info.itsthesky.webhooks.elements.WebhookExpression;
import org.jetbrains.annotations.NotNull;

@Name("Builder TTS State")
@Description({"Change the tts state of any webhook message builder.",
"If enabled, Discord will read the message aloud to the user."})
@Examples("set webhook tts of builder to true")
@Module("Webhooks")
public class BuilderTTS extends WebhookExpression<Boolean> {

	static {
		register(BuilderTTS.class, Boolean.class, "tts [state]");
	}

	@Override
	public void change(Changer.ChangeMode mode, Boolean value, WebhookMessageBuilder builder) {
		if (mode == Changer.ChangeMode.SET)
			builder.setTTS(value);
	}

	@Override
	public @NotNull String getProperty() {
		return "tts state";
	}
}
