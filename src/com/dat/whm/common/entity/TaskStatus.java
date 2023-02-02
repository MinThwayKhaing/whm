package com.dat.whm.common.entity;

public enum TaskStatus {

	FORWARD {
		public String toString() {
			return "Forward";
		}
	}
	, DELAY {
		public String toString() {
			return "Delay";
		}
	};

	public abstract String toString();
}