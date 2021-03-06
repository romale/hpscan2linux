package HPScan2Linux.HPScan2Linux;

import org.apache.http.Header;
import org.apache.http.HttpResponse;

import com.sun.org.apache.xerces.internal.util.URI;
import com.sun.org.apache.xerces.internal.util.URI.MalformedURIException;

public class ScanJobsResponse extends ResponseExecutor {

	@Override
	public void init(HttpResponse response) {
		m_url = null;
		if (response != null) {
			Header[] headers = response.getAllHeaders();
			for (Header header : headers) {
				if (header.getName().equalsIgnoreCase("Location")) {
					String url = header.getValue();
					URI uri = null;
					try {
						uri = new URI(url);
					} catch (MalformedURIException e) {
						e.printStackTrace();
					}
					if (uri != null)
						m_url = uri.getPath();
					
					StateService.getInstance().setJobURL(m_url);
					
					break;
				}
			}
		}
	}

	@Override
	public void execute() {
		// TODO Auto-generated method stub
		if (m_url != null)
			m_events.add(EventFactory.createEvent(m_url));
	}

	private String m_url;
}
