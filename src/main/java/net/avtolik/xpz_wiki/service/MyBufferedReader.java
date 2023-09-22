package net.avtolik.xpz_wiki.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;

public class MyBufferedReader extends BufferedReader {

	private boolean eof= false;
	MyBufferedReader(Reader r) {
		super(r);
	}

	@Override
	public String readLine() throws IOException {
		String line = super.readLine();
		if(line.startsWith("battleGame")) {

			return null;
		}
		return line;
	}

	@Override
	public int read(char cbuf[], int off, int len) throws IOException {
		if(eof)
			return 0;
		int n = super.read(cbuf, off, len);
		boolean eofDetected = false;

		if (n > 0) {
			if (Character.isHighSurrogate(cbuf[n - 1])) {
				int oneMore = super.read(cbuf, n, 1);
				if (oneMore != -1) {
					n += oneMore;
				} else {
					eofDetected = true;
				}
			}
			StringBuilder builder  = new StringBuilder( n) .append(cbuf, 0, n);
			String line = builder.toString();
			int indexOf = line.indexOf("battleGame:");
			if(indexOf != -1) {
				eof = true;
				return indexOf-1;
			}
		}



		return n;

	}



}