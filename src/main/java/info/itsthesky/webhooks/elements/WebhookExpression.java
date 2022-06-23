package info.itsthesky.webhooks.elements;

import ch.njol.skript.classes.Changer;
import ch.njol.skript.expressions.base.SimplePropertyExpression;
import club.minnced.discord.webhook.send.WebhookMessageBuilder;
import info.itsthesky.disky.api.skript.EasyElement;
import org.bukkit.event.Event;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.ParameterizedType;

public abstract class WebhookExpression<T> extends SimplePropertyExpression<WebhookMessageBuilder, T> {

	public static <T, E extends WebhookExpression<T>> void register(Class<E> expr, Class<T> type, String name) {
		register(expr, type, "webhook "+name, "webhookbuilder");
	}

	@Override
	public @Nullable
	T convert(WebhookMessageBuilder webhookMessageBuilder) {
		return null;
	}

	public abstract void change(Changer.ChangeMode mode, T value, WebhookMessageBuilder builder);

	@Override
	public void change(@NotNull Event e, Object @NotNull [] delta, Changer.@NotNull ChangeMode mode) {
		if (!EasyElement.isValid(delta))
			return;
		final WebhookMessageBuilder builder = EasyElement.parseSingle(getExpr(), e, null);
		if (builder == null)
			return;
		change(mode, (T) delta[0], builder);
	}

	@Override
	public Class<?> @NotNull [] acceptChange(Changer.@NotNull ChangeMode mode) {
		if (EasyElement.equalAny(mode, Changer.ChangeMode.SET, Changer.ChangeMode.ADD, Changer.ChangeMode.REMOVE))
			return new Class[]{getReturnType()};
		return new Class[0];
	}

	@Override
	@SuppressWarnings("unchecked")
	public @NotNull Class<? extends T> getReturnType() {
		return ((Class<T>) ((ParameterizedType) getClass()
				.getGenericSuperclass()).getActualTypeArguments()[1]);
	}

	@Override
	protected @NotNull String getPropertyName() {
		return "builder " + getProperty();
	}

	public abstract @NotNull String getProperty();
}
