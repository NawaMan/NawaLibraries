package net.nawaman.text;

import java.util.HashMap;
import java.util.Vector;

final public class UText {
	
	private UText() {}

	static public CharSequence AddTabs(CharSequence CS) {
		return AddTabs(CS, 1, true, null);
	}
	static public CharSequence AddTabs(CharSequence CS, Object ExtraInfo) {
		return AddTabs(CS, 1, true, ExtraInfo);
	}

	static public CharSequence AddTabs(CharSequence CS, boolean IsAddToStart) {
		return AddTabs(CS, 1, IsAddToStart, null);
	}
	static public CharSequence AddTabs(CharSequence CS, boolean IsAddToStart, Object ExtraInfo) {
		return AddTabs(CS, 1, IsAddToStart, ExtraInfo);
	}

	static public CharSequence AddTabs(CharSequence CS, int TabCount, boolean IsAddToStart) {
		return AddTabs(CS, TabCount, IsAddToStart, null);
	}

	static public CharSequence AddTabs(CharSequence CS, int TabCount, boolean IsAddToStart, Object ExtraInfo) {
		if(CS == null) return null;
		
		// No need to add Tabs
		String Str = CS.toString();
		if(!IsAddToStart && !Str.contains("\n"))
			return CS;
		
		// Not a Text
		if(!(CS instanceof Text))
			return UTextPrivate.AddTabs_ToString(CS.toString(), TabCount, IsAddToStart);
		
		Text T = (Text)CS;
		if(T instanceof VirtualText)
			throw new UnsupportedOperationException("Adding Text to a VirtualText is not yet supported.");
		
		if((T instanceof  AppendableText) || (T instanceof  FixedLengthText)) {
			if(ExtraInfo == T.getExtraInfo()) {
				if(T instanceof  AppendableText) return new AppendableText( T.getExtraInfo(), UTextPrivate.AddTabs_ToString(CS.toString(), TabCount, IsAddToStart));
				if(T instanceof FixedLengthText) return new FixedLengthText(T.getExtraInfo(), UTextPrivate.AddTabs_ToString(CS.toString(), TabCount, IsAddToStart));
				
			} else {
				StructureText SText = new StructureText(ExtraInfo);
				UTextPrivate.AddTabs_ToNonStructureText(SText, T, TabCount, IsAddToStart, ExtraInfo);
				return SText;
			}
		}
		
		StructureText SText = new StructureText(T.getExtraInfo());
		UTextPrivate.AddTabs_ToStructureText(SText, (StructureText)T, TabCount, IsAddToStart, ExtraInfo);
		return SText;
	}

	static public void main(String ... Args) {		
		Text ST = new StructureText();
		FixedLengthText FText1 = new FixedLengthText("Line 1\nLi");
		FixedLengthText FText2 = new FixedLengthText("ne 2\nLine 3\n");
		((StructureText)ST).append(FText1);
		((StructureText)ST).append(FText2);
		
		System.out.println("ST:\n" + ST.toString());
		System.out.println("ST:\n" + ST.toDetail());
		
		Text NewST = (Text)AddTabs(ST, 1, true, "Me");
		System.out.println("NewST:\n" + NewST.toString());
		System.out.println("NewST:\n" + NewST.toDetail());

		
		NewST = (Text)AddTabs(ST, 1, false, "Me");
		System.out.println("NewST:\n" + NewST.toString());
		System.out.println("NewST:\n" + NewST.toDetail());
		
		NewST = (Text)AddTabs(ST, 2, true, "Me");
		System.out.println("NewST:\n" + NewST.toString());
		System.out.println("NewST:\n" + NewST.toDetail());
		
		NewST = (Text)AddTabs(ST, 2, false, "Me");
		System.out.println("NewST:\n" + NewST.toString());
		System.out.println("NewST:\n" + NewST.toDetail());
		


		ST = new FixedLengthText("You", "Line 1\nLi\nne 2\nLine 3\n");
		System.out.println("ST:\n" + ST.toString());
		System.out.println("ST:\n" + ST.toDetail());
		
		NewST = (StructureText)AddTabs(ST, 1, false, "Me");
		System.out.println("NewST:\n" + NewST.toString());
		System.out.println("NewST:\n" + NewST.toDetail());
		
		NewST = (StructureText)AddTabs(ST, 1, true, "Me");
		System.out.println("NewST:\n" + NewST.toString());
		System.out.println("NewST:\n" + NewST.toDetail());
	}
}

class UTextPrivate {
	
	// Return empty when 
	static String[] SplitString(String Str) {
		Vector<String> Strs = new Vector<String>();
		
		int Position = 0;
		int Index    = 0;
		while(true) {
			Index = Str.indexOf('\n', Position);
			if(Index != -1) Strs.add(Str.substring(Position, Index));
			else            Strs.add(Str.substring(Position));
			
			if(Index == -1) break;
			Position = Index + 1;
		}
		return Strs.toArray(new String[Strs.size()]);
	}
	
	static Text                     NewLine196  = new FixedLengthText("UText::AddTabs(...) on (196)", "\n");
	static Text                     NewLine223  = new FixedLengthText("UText::AddTabs(...) on (223)", "\n");
	static HashMap<Integer, String> TabStrs     = new HashMap<Integer, String>();
	static HashMap<Integer, Text>   TabTexts193 = new HashMap<Integer, Text>();
	static HashMap<Integer, Text>   TabTexts206 = new HashMap<Integer, Text>();
	static HashMap<Integer, Text>   TabTexts216 = new HashMap<Integer, Text>();
	
	static private String GetTabStrs(int TabCount) {
		String Str = TabStrs.get(TabCount);
		if(Str != null)
			return Str;
		
		StringBuilder TabSB = new StringBuilder();
		for(int t = 0; t < TabCount; t++) TabSB.append("\t");
		
		TabStrs.put(TabCount, Str = TabSB.toString());
		return Str;
	}
	static private Text GetTabTexts_193(int TabCount) {
		Text Txt = TabTexts193.get(TabCount); if(Txt != null) return Txt;
		TabTexts193.put(TabCount, Txt = new FixedLengthText("UText::AddTabs(...) on (193)", GetTabStrs(TabCount)));
		return Txt;
	}
	static private Text GetTabTexts_206(int TabCount) {
		Text Txt = TabTexts206.get(TabCount); if(Txt != null) return Txt;
		TabTexts206.put(TabCount, Txt = new FixedLengthText("UText::AddTabs(...) on (206)", GetTabStrs(TabCount)));
		return Txt;
	}
	static private Text GetTabTexts_216(int TabCount) {
		Text Txt = TabTexts216.get(TabCount); if(Txt != null) return Txt;
		TabTexts216.put(TabCount, Txt = new FixedLengthText("UText::AddTabs(...) on (216)", GetTabStrs(TabCount)));
		return Txt;
	}
	
	static String AddTabs_ToString(String Str, int TabCount, boolean IsAddToStart) {
		if(Str == null)
			return null;
		
		StringBuilder TabSB = new StringBuilder();
		for(int t = 0; t < TabCount; t++) TabSB.append("\t");
		String Tab = TabSB.toString();
		
		if(!Str.contains("\n") || (TabCount < 1))
			return (IsAddToStart ? Tab : "") + Str;
		
		
		String[]      Parts = SplitString(Str);
		StringBuilder SB    = new StringBuilder();
		for(int p = 0; p < Parts.length; p++) {
			if(IsAddToStart || (p != 0) || Str.startsWith("\n"))
				SB.append(Tab);
			
			SB.append(Parts[p]);
			
			if(p != (Parts.length - 1))
				SB.append("\n");
		}
		
		return SB.toString();
	}
	
	static void AddTabs_ToString(StructureText SText, String Str, int TabCount, boolean IsAddToStart, Object ExtraInfo) {
		if((Str == null) || !Str.contains("\n") || (TabCount < 1)) {
			SText.append(new FixedLengthText(Str));
			return;
		}
		
		String[] Parts = SplitString(Str);
		for(int p = 0; p < Parts.length; p++) {
			if(IsAddToStart || (p != 0) || Str.startsWith("\n"))
				SText.append(GetTabTexts_193(TabCount));
			
			SText.append(new FixedLengthText(ExtraInfo, Parts[p]));
			SText.append(NewLine196);
		}
	}
	static void AddTabs_ToNonStructureText(StructureText SText, Text T, int TabCount, boolean IsAddToStart, Object ExtraInfo) {
		if(T == null)
			return;
			
		String Str = T.toString();

		if((Str == null) || !Str.contains("\n") || (TabCount < 1)) {
			if(IsAddToStart) SText.append(GetTabTexts_206(TabCount));

			SText.append(T);
			return;
		}
		
		String[] Parts    = SplitString(Str);
		int      LastPart = Parts.length - 1;
		for(int p = 0; p < Parts.length; p++) {
			if(IsAddToStart || (p != 0) || Str.startsWith("\n"))
				SText.append(GetTabTexts_216(TabCount));
			
			String aStr = Parts[p];
			if((aStr != null) && (aStr.length() != 0))
				SText.append(new FixedLengthText(T.getExtraInfo(), aStr));
			
			if(p != LastPart)
				SText.append(NewLine223);
		}
	}
	static void AddTabs_ToStructureText(StructureText SText, StructureText T, int Tab, boolean IsAddToStart, Object ExtraInfo) {
		if(T.getStructureCount() == 0) return;
		
		Text FirstText = T.getStructure(0);
		if(FirstText instanceof StructureText)
			 AddTabs_ToStructureText   (SText, (StructureText)FirstText, Tab, IsAddToStart, ExtraInfo);
		else AddTabs_ToNonStructureText(SText,                FirstText, Tab, IsAddToStart, ExtraInfo);
		
		
		for(int s = 1; s < T.getStructureCount(); s++) {
			Text SubText = T.getStructure(s);
			if(SubText instanceof StructureText)
				 AddTabs_ToStructureText   (SText, (StructureText)SubText, Tab, false, ExtraInfo);
			else AddTabs_ToNonStructureText(SText,                SubText, Tab, false, ExtraInfo);
		}
	}
}