package Parser;


public class ParserFactory {
	
	public static Parser createParser(String type) {
		Parser parser = null ;
		
		switch (type) { 
			case MassParser.TAG: parser = new MassParser();
			break;
			case SpringParser.TAG: parser = new SpringParser();
			break;
			case WallParser.TAG: parser = new WallParser();
			break;
			case ViscosityParser.TAG: parser = new ViscosityParser();
			break;
			case GravityParser.TAG: parser = new GravityParser();
			break;
			case CenterMassParser.TAG: parser = new CenterMassParser();
			break;	
			case MuscleParser.TAG: parser = new MuscleParser();
			break;	
			case FixedMassParser.TAG: parser = new FixedMassParser();
		}
		
		return parser;
	}
}
