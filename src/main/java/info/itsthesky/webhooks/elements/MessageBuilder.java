package info.itsthesky.webhooks.elements;

import ch.njol.skript.Skript;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Name;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import club.minnced.discord.webhook.send.WebhookMessageBuilder;
import org.bukkit.event.Event;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;

@Name("Webhook Message Builder")
@Description("The current webhook message builder from the current 'make message' scope.")
public class MessageBuilder extends SimpleExpression<WebhookMessageBuilder> {

    static {
        Skript.registerExpression(
                MessageBuilder.class,
                WebhookMessageBuilder.class,
                ExpressionType.SIMPLE,
                "[the] [last] [message] builder"
        );
    }

    private MakeMessage section;

    @Override
    protected @ParametersAreNonnullByDefault WebhookMessageBuilder @NotNull [] get(@NotNull Event e) {
        return new WebhookMessageBuilder[] {section.getBuilder()};
    }

    @Override
    public boolean isSingle() {
        return true;
    }

    @Override
    public @NotNull Class<? extends WebhookMessageBuilder> getReturnType() {
        return WebhookMessageBuilder.class;
    }

    @Override
    public @NotNull String toString(@Nullable Event e, boolean debug) {
        return "messmessage builder";
    }

    @Override
    public boolean init(Expression<?> @NotNull [] exprs, int matchedPattern, @NotNull Kleenean isDelayed, SkriptParser.@NotNull ParseResult parseResult) {
        section = getParser().getCurrentSection(MakeMessage.class);
        if (section == null) {
            Skript.error("This expression cannot be used outside a message builder scope");
            return false;
        }
        return true;
    }

}
