package com.arun.parallel;

import java.io.Serializable;

@FunctionalInterface
public interface ObjectConsumer {

	Boolean accept(Serializable obj);
}
