package info.itsthesky.webhooks.elements;

import ch.njol.skript.classes.Changer;
import club.minnced.discord.webhook.send.WebhookMessageBuilder;
import info.itsthesky.disky.api.skript.EasyElement;
import info.itsthesky.disky.api.skript.MultiplyPropertyExpression;
import org.bukkit.event.Event;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.ParameterizedType;

public abstract class MultipleWebhookExpression<T> extends MultiplyPropertyExpression<WebhookMessageBuilder, T> {

	public static <T, E extends MultipleWebhookExpression<T>> void register(Class<E> expr, Class<T> type, String name) {
		register(expr, type, "webhook "+name, "webhookmessage");
	}

	@Override
	public @Nullable
	T[] convert(WebhookMessageBuilder webhookMessageBuilder) {
		return null;
	}

	public abstract void change(Changer.ChangeMode mode, T[] values, WebhookMessageBuilder builder);

	@Override
	public void change(@NotNull Event e, Object @NotNull [] delta, Changer.@NotNull ChangeMode mode) {
		if (!EasyElement.isValid(delta))
			return;
		final WebhookMessageBuilder builder = EasyElement.parseSingle(getExpr(), e, null);
		if (builder == null)
			return;
		change(mode, (T[]) delta, builder);
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
				.getGenericSuperclass()).getActualTypeArguments()[0]);
	}

	@Override
	protected @NotNull String getPropertyName() {
		return "builder " + getProperty();
	}

	public abstract @NotNull String getProperty();
}
