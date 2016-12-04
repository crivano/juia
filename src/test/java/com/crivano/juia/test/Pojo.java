package com.crivano.juia.test;

import com.crivano.juia.annotations.Edit;

public class Pojo {
	Long id;

	@Edit(caption = "Nome", hint = "Entre seu nome aqui.")
	String name;

	@Edit(caption = "Ativo", hint = "Marque se estiver ativo.")
	boolean active;
}
