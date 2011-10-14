package service.component.extender;

import java.util.List;

import javassist.bytecode.annotation.AnnotationMemberValue;
import javassist.bytecode.annotation.ArrayMemberValue;
import javassist.bytecode.annotation.BooleanMemberValue;
import javassist.bytecode.annotation.ByteMemberValue;
import javassist.bytecode.annotation.CharMemberValue;
import javassist.bytecode.annotation.ClassMemberValue;
import javassist.bytecode.annotation.DoubleMemberValue;
import javassist.bytecode.annotation.EnumMemberValue;
import javassist.bytecode.annotation.FloatMemberValue;
import javassist.bytecode.annotation.IntegerMemberValue;
import javassist.bytecode.annotation.LongMemberValue;
import javassist.bytecode.annotation.MemberValue;
import javassist.bytecode.annotation.MemberValueVisitor;
import javassist.bytecode.annotation.ShortMemberValue;
import javassist.bytecode.annotation.StringMemberValue;

public class PropertiesMemberValueVisitor implements MemberValueVisitor {
	
	private final List<String> properties;

	public PropertiesMemberValueVisitor(List<String> properties) {
		this.properties = properties;
	}

	@Override
	public void visitArrayMemberValue(ArrayMemberValue value) {
		MemberValue[] values = value.getValue();
		for (MemberValue memberValue : values) {
			memberValue.accept(this);
		}
	}
	
	@Override
	public void visitStringMemberValue(StringMemberValue value) {
		String propertyValue = value.getValue();
		getProperties().add(propertyValue);
	}
	
	@Override
	public void visitAnnotationMemberValue(AnnotationMemberValue value) {
	}

	@Override
	public void visitBooleanMemberValue(BooleanMemberValue value) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visitByteMemberValue(ByteMemberValue value) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visitCharMemberValue(CharMemberValue value) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visitClassMemberValue(ClassMemberValue value) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visitDoubleMemberValue(DoubleMemberValue value) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visitEnumMemberValue(EnumMemberValue value) {
	}

	@Override
	public void visitFloatMemberValue(FloatMemberValue value) {
	}

	@Override
	public void visitIntegerMemberValue(IntegerMemberValue value) {
	}

	@Override
	public void visitLongMemberValue(LongMemberValue value) {
	}

	@Override
	public void visitShortMemberValue(ShortMemberValue value) {
	}

	public List<String> getProperties() {
		return properties;
	}

	

}
