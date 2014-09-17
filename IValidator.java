package org.zen.Algorithm;

import java.util.Collection;

public interface IValidator<T> {
	public boolean isValid(Collection<T> col);
}
