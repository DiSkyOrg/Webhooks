package info.itsthesky.webhooks.elements.builder;

import ch.njol.skript.Skript;
import ch.njol.skript.classes.Changer;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import club.minnced.discord.webhook.send.WebhookMessageBuilder;
import info.itsthesky.disky.api.generator.Module;
import info.itsthesky.webhooks.elements.MultipleWebhookExpression;
import org.jetbrains.annotations.NotNull;

import java.io.File;

@Name("Builder Files")
@Description("Change the files of any webhook message builder.")
@Examples({"set webhook files of builder to \"file/path\"",
"add \"file/path\" to webhook files of builder"})
@Module("Webhooks")
public class BuilderFiles extends MultipleWebhookExpression<String> {

	static {
		register(BuilderFiles.class, String.class, "files");
	}

	@Override
	public void change(Changer.ChangeMode mode, String[] values, WebhookMessageBuilder builder) {
		if (mode == Changer.ChangeMode.SET)
			builder.resetFiles();
		for (String path : values) {
			final File file = new File(path);
			if (!file.exists()) {
				Skript.error("File '" + file.getPath() + "' does not exist.");
				continue;
			}
			builder.addFile(file);
		}
	}

	@Override
	public @NotNull String getProperty() {
		return "files";
	}
}
