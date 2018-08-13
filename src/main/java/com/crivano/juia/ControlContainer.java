package com.crivano.juia;

import java.util.List;

import com.crivano.juia.annotations.Global;
import com.crivano.juia.control.Control;

public interface ControlContainer {
	List<Control> getControls();

	public String getSingular();

	public String getPlural();

	public Global.Gender getGender();
}
