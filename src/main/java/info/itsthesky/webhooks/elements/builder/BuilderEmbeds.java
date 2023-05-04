package info.itsthesky.webhooks.elements.builder;

import ch.njol.skript.classes.Changer;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import club.minnced.discord.webhook.send.WebhookEmbed;
import club.minnced.discord.webhook.send.WebhookEmbedBuilder;
import club.minnced.discord.webhook.send.WebhookMessageBuilder;
import info.itsthesky.disky.api.generator.Module;
import info.itsthesky.webhooks.elements.MultipleWebhookExpression;
import net.dv8tion.jda.api.EmbedBuilder;
import org.jetbrains.annotations.NotNull;

import java.util.stream.Stream;

@Name("Builder Embeds")
@Description("Change the embeds of any webhook message builder.")
@Examples({"set webhook embeds of builder to last embed builder",
"add last embed builder to webhook embeds of builder"})
@Module("Webhooks")
public class BuilderEmbeds extends MultipleWebhookExpression<EmbedBuilder> {

	static {
		register(BuilderEmbeds.class, EmbedBuilder.class, "embeds");
	}

	@Override
	public void change(Changer.ChangeMode mode, EmbedBuilder[] values, WebhookMessageBuilder builder) {
		if (mode == Changer.ChangeMode.SET)
			builder.resetEmbeds();
		builder.addEmbeds(Stream.of(values)
				.map(EmbedBuilder::build)
				.map(WebhookEmbedBuilder::fromJDA)
				.map(WebhookEmbedBuilder::build)
				.toArray(WebhookEmbed[]::new));
	}

	@Override
	public @NotNull String getProperty() {
		return "embeds";
	}
}
