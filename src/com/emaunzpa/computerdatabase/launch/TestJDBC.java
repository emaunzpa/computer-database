package com.emaunzpa.computerdatabase.launch;

import java.text.ParseException;
import com.emaunzpa.computerdatabase.UI.CommandeLigneInterface;
import com.emaunzpa.computerdatabase.exception.ComputerWithoutNameException;
import com.emaunzpa.computerdatabase.exception.DiscontinuedBeforeIntroducedException;
import com.emaunzpa.computerdatabase.exception.IncoherenceBetweenDateException;
import com.emaunzpa.computerdatabase.exception.NoComputerFoundException;

public class TestJDBC {

	public static void main(String[] args) throws ParseException, ComputerWithoutNameException, IncoherenceBetweenDateException, DiscontinuedBeforeIntroducedException, NoComputerFoundException {
				
		CommandeLigneInterface cli = new CommandeLigneInterface();
		cli.run();
		
	}

}
