package service.component.demo;

import service.component.annotations.Activate;
import service.component.annotations.Component;

@Component(immediat=true, properties={
	"service.component.demo.property=true",
	"service.component.demo.name=Tux2323",
	"osgi.rocks.property=Yes"
})
public class DemoComponent {
	
	@Activate
	public void init()
	{
	}
	
}
