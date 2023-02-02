package com.dat.whm.idgen.service.interfaces;

import com.dat.whm.idgen.exception.CustomIDGeneratorException;

public interface ICustomIDGenerator {
	public String getNextId(String genName) throws CustomIDGeneratorException;
}
