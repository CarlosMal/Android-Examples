package com.tonilopezmr.dagger2rxjava.di;

import java.lang.annotation.Retention;

import javax.inject.Scope;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * @author Antonio López.
 */
@Scope
@Retention(RUNTIME)
public @interface UIThread {
}
