package cms.www.util;

import java.io.IOException;
import java.io.Writer;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;

public class DOMWriter
{
	public static void write(Document doc, Writer writer) throws IOException
	{
		write(doc.getDocumentElement(), writer, 0);
	}
	
	private static void write(Element element, Writer writer, int numIndents) throws IOException
	{
		for (int i = 0; i < numIndents; i++) writer.write("   ");
		writer.write("&lt;" + element.getTagName());
		String namespace = element.getNamespaceURI();
		if (namespace != null) {
		    writer.write(" namespace=\"" + namespace + "\"");
		}
		NamedNodeMap attrs = element.getAttributes();
		for (int i = 0; i < attrs.getLength(); i++)
		{
			Node attr = attrs.item(i);
			writer.write(" " + attr.getNodeName() + "=\"" + attr.getNodeValue() + "\""); 
		}
		writer.write("&gt;\n");
		NodeList children = element.getChildNodes();
		for (int i = 0; i < children.getLength(); i++)
		{
			Node node = children.item(i);
			switch(node.getNodeType())
			{
				case Node.ELEMENT_NODE:
					write((Element)children.item(i), writer, numIndents + 1);
					break;
				case Node.TEXT_NODE:
					writer.write(StringUtil.formatWebString(((Text)children.item(i)).getData()) + "\n");
					break;
			}
		}
		if (children.getLength() > 0) {
		    for (int i = 0; i < numIndents; i++) writer.write("   ");
		    writer.write("&lt;" + "/" + element.getTagName() + "&gt;\n");
		}
	}
}
