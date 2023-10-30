### ConfigurationClassParser#parse(Set<BeanDefinitionHolder>)

解析用户配置

DeferredImportSelectorHandler#process
处理预定义配置

### ConfigurationClassBeanDefinitionReader#loadBeanDefinitions

加载bean定义

### PostProcessorRegistrationDelegate#invokeBeanDefinitionRegistryPostProcessors

初始化bean处理

### AbstractBeanFactory#doGetBean(->getSingleton)

```java

protected Object createBean(String beanName,RootBeanDefinition mbd,@Nullable Object[]args)
        throws BeanCreationException{

        }
```

#### DefaultListableBeanFactory#getBeanNamesForType

```java 
@Override
public String[]getBeanNamesForType(@Nullable Class<?> type){
        return getBeanNamesForType(type,true,true);
        }
```

创建或获取Bean

### ***AbstractBeanFactory#isTypeMatch/resolveBeanClass/doResolveBeanClass

```java
protected<T> T doGetBean(
        String name,@Nullable Class<T> requiredType,@Nullable Object[]args,boolean typeCheckOnly)
        throws BeansException{
        //...
        // Check if bean definition exists in this factory.
        BeanFactory parentBeanFactory=getParentBeanFactory();
        if(parentBeanFactory!=null&&!containsBeanDefinition(beanName)){
        }
        //...
        }
protected BeanWrapper createBeanInstance(String beanName,RootBeanDefinition mbd,@Nullable Object[]args){
        }
protected Class<?> predictBeanType(String beanName,RootBeanDefinition mbd,Class<?>...typesToMatch){

        }
protected Class<?> determineTargetType(String beanName,RootBeanDefinition mbd,Class<?>...typesToMatch){

        }
protected Class<?> resolveBeanClass(RootBeanDefinition mbd,String beanName,Class<?>...typesToMatch)
        }
protected Object resolveBeforeInstantiation(String beanName,RootBeanDefinition mbd){

        }
protected boolean isTypeMatch(String name,ResolvableType typeToMatch,boolean allowFactoryBeanInit)
        throws NoSuchBeanDefinitionException{
        //...
        // No singleton instance found -> check bean definition.
        BeanFactory parentBeanFactory=getParentBeanFactory();
        if(parentBeanFactory!=null&&!containsBeanDefinition(beanName)){
        // No bean definition found in this factory -> delegate to parent.
        return parentBeanFactory.isTypeMatch(originalBeanName(name),typeToMatch);
        }

        // Retrieve corresponding bean definition.
        RootBeanDefinition mbd=getMergedLocalBeanDefinition(beanName);
        //...
        }

public Class<?> getType(String name, boolean allowFactoryBeanInit) throws NoSuchBeanDefinitionException {
    //...
        BeanFactory parentBeanFactory = getParentBeanFactory();
        if (parentBeanFactory != null && !containsBeanDefinition(beanName)) {
        // No bean definition found in this factory -> delegate to parent.
        return parentBeanFactory.getType(originalBeanName(name));
        }

        RootBeanDefinition mbd = getMergedLocalBeanDefinition(beanName);
        Class<?> beanClass = predictBeanType(beanName, mbd);
        //...
        }
public BeanDefinition getMergedBeanDefinition(String name) throws BeansException {
        String beanName = transformedBeanName(name);
        // Efficiently check whether bean definition exists in this factory.
        if (!containsBeanDefinition(beanName) && getParentBeanFactory() instanceof ConfigurableBeanFactory parent) {
        return parent.getMergedBeanDefinition(beanName);
        }
        // Resolve merged bean definition locally.
        return getMergedLocalBeanDefinition(beanName);
        }
```

非常重要的一个方法，决定了bean的（加载器）类型,优先查询父容器

### AbstractBeanDefinition#resolveBeanClass

该方法决定了每个bean定义的类型,实际调用的是Class#forName(String name, boolean initialize,
ClassLoader loader)

### AbstractAutowireCapableBeanFactory#doCreateBean

```java
protected Object doCreateBean(String beanName,RootBeanDefinition mbd,@Nullable Object[]args)
        throws BeanCreationException{

        }
```

### AbstractAutowireCapableBeanFactory#doCreateBean

创建Bean

### TypeConverterSupport#convertIfNecessary

```java
public<T> T convertIfNecessary(@Nullable Object value,@Nullable Class<T> requiredType,
@Nullable TypeDescriptor typeDescriptor)throws TypeMismatchException{

        Assert.state(this.typeConverterDelegate!=null,"No TypeConverterDelegate");
        try{
        return this.typeConverterDelegate.convertIfNecessary(null,null,value,requiredType,typeDescriptor);
        }
        catch(ConverterNotFoundException|IllegalStateException ex){
        throw new ConversionNotSupportedException(value,requiredType,ex);
        }
        catch(ConversionException|IllegalArgumentException ex){
        throw new TypeMismatchException(value,requiredType,ex);
        }
        }
```

### AbstractAutowireCapableBeanFactory#applyBeanPostProcessorsAfterInitialization

组装Bean

### AbstractAutoProxyCreator

#### AbstractAutoProxyCreator#postProcessAfterInitialization

```java
postProcessAfterInitialization(@Nullable Object bean,String beanName){
        if(bean!=null){
        Object cacheKey=getCacheKey(bean.getClass(),beanName);
        if(this.earlyProxyReferences.remove(cacheKey)!=bean){
        //AOP
        return wrapIfNecessary(bean,beanName,cacheKey);
        }
        }
        return bean;
        }
```

#### AbstractAutoProxyCreator#wrapIfNecessary

```java
protected Object wrapIfNecessary(Object bean,String beanName,Object cacheKey){
        // ...
        Object proxy=createProxy(
        bean.getClass(),beanName,specificInterceptors,new SingletonTargetSource(bean));
        // ...
        }

```

#### AbstractAutoProxyCreator#buildProxy

```java
private Object buildProxy(Class<?> beanClass,@Nullable String beanName,
@Nullable Object[]specificInterceptors,TargetSource targetSource,boolean classOnly){
        //...
        ProxyFactory proxyFactory=new ProxyFactory();
        proxyFactory.copyFrom(this);
        //...
        ClassLoader classLoader=getProxyClassLoader();
        //...
        return(classOnly?proxyFactory.getProxyClass(classLoader):proxyFactory.getProxy(classLoader));
        }
```

### ProxyFactory#getProxy

```java
public Object getProxy(@Nullable ClassLoader classLoader){
        return createAopProxy().getProxy(classLoader);
        }
```

### CglibAopProxy#buildProxy

```java
private Object buildProxy(@Nullable ClassLoader classLoader,boolean classOnly){
        //...
        Enhancer enhancer=createEnhancer();
        if(classLoader!=null){
        enhancer.setClassLoader(classLoader);
        if(classLoader instanceof SmartClassLoader smartClassLoader&&
        smartClassLoader.isClassReloadable(proxySuperClass)){
        enhancer.setUseCache(false);
        }
        }
        enhancer.setSuperclass(proxySuperClass);
        enhancer.setInterfaces(AopProxyUtils.completeProxiedInterfaces(this.advised));
        enhancer.setNamingPolicy(SpringNamingPolicy.INSTANCE);
        enhancer.setAttemptLoad(true);
        enhancer.setStrategy(new ClassLoaderAwareGeneratorStrategy(classLoader));
        return(classOnly?createProxyClass(enhancer):createProxyClassAndInstance(enhancer,callbacks));
        }
```

#### ObjenesisCglibAopProxy#createProxyClassAndInstance

```java
protected Object createProxyClassAndInstance(Enhancer enhancer,Callback[]callbacks){
        //该方法决定了代理类的类型，使用自定义类时需要特别注意
        //不然可能会导致代理类与被代理类因为类加载器的原因导致类型不匹配
        Class<?> proxyClass=enhancer.createClass();


        if(objenesis.isWorthTrying()){
        try{
        proxyInstance=objenesis.newInstance(proxyClass,enhancer.getUseCache());
        }
        catch(Throwable ex){
        //...
        }
        }

        if(proxyInstance==null){
        // Regular instantiation via default constructor...
        try{
        Constructor<?> ctor=(this.constructorArgs!=null?
        proxyClass.getDeclaredConstructor(this.constructorArgTypes):
        proxyClass.getDeclaredConstructor());
        ReflectionUtils.makeAccessible(ctor);
        proxyInstance=(this.constructorArgs!=null?
        ctor.newInstance(this.constructorArgs):ctor.newInstance());
        }
        catch(Throwable ex){
        throw new AopConfigException("Unable to instantiate proxy using Objenesis, "+
        "and regular proxy instantiation via default constructor fails as well",ex);
        }
        }
        return proxyInstance;
        }
```

#### Enhancer#createClass

```java
public Class createClass(){
        classOnly=true;
        return(Class)createHelper();
        }
```

#### Enhancer#createHelper

```java
private Object createHelper(){
        preValidate();
        // SPRING PATCH BEGIN
        Object key=new EnhancerKey((superclass!=null?superclass.getName():null),
        (interfaces!=null?Arrays.asList(ReflectUtils.getNames(interfaces)):null),
        (filter==ALL_ZERO?null:new WeakCacheKey<>(filter)),
        Arrays.asList(callbackTypes),
        useFactory,
        interceptDuringConstruction,
        serialVersionUID);
        // SPRING PATCH END
        this.currentKey=key;
        Object result=super.create(key);
        return result;
        }
```

#### Enhancer#generate

```java
    protected Class generate(ClassLoaderData data){
        validate();
        if(superclass!=null){
        setNamePrefix(superclass.getName());
        }
        else if(interfaces!=null){
        setNamePrefix(interfaces[ReflectUtils.findPackageProtected(interfaces)].getName());
        }
        return super.generate(data);
        }
```

#### AbstractClassGenerator#generate

```java
protected Class generate(ClassLoaderData data){
        Class gen;
        Object save=CURRENT.get();
        CURRENT.set(this);
        try{
        ClassLoader classLoader=data.getClassLoader();
//...
synchronized (classLoader){
        //生成代理类目
        String name=generateClassName(data.getUniqueNamePredicate());
        data.reserveName(name);
        this.setClassName(name);
        }
        if(attemptLoad){
        try{
// SPRING PATCH BEGIN
synchronized (classLoader){ // just in case
        gen=ReflectUtils.loadClass(getClassName(),classLoader);
        }
        // SPRING PATCH END
        return gen;
        }
        catch(ClassNotFoundException e){
        // ignore
        }
        }
        // SPRING PATCH BEGIN
        if(inNativeImage){
        throw new UnsupportedOperationException("CGLIB runtime enhancement not supported on native image. "+
        "Make sure to include a pre-generated class on the classpath instead: "+getClassName());
        }
        // SPRING PATCH END
        byte[]b=strategy.generate(this);
        String className=ClassNameReader.getClassName(new ClassReader(b));
        ProtectionDomain protectionDomain=getProtectionDomain();
synchronized (classLoader){ // just in case
        // SPRING PATCH BEGIN
        gen=ReflectUtils.defineClass(className,b,classLoader,protectionDomain,contextClass);
        // SPRING PATCH END
        }
        return gen;
        }
        catch(RuntimeException|Error ex){
        throw ex;
        }
        catch(Exception ex){
        throw new CodeGenerationException(ex);
        }
        finally{
        CURRENT.set(save);
        }
        }
```

```java
protected Object create(Object key){
        try{
        ClassLoader loader=getClassLoader();
        Map<ClassLoader, ClassLoaderData> cache=CACHE;
        ClassLoaderData data=cache.get(loader);
        if(data==null){
synchronized (AbstractClassGenerator.class){
        cache=CACHE;
        data=cache.get(loader);
        if(data==null){
        Map<ClassLoader, ClassLoaderData> newCache=new WeakHashMap<>(cache);
        data=new ClassLoaderData(loader);
        newCache.put(loader,data);
        CACHE=newCache;
        }
        }
        }
        this.key=key;
        Object obj=data.get(this,getUseCache());
        if(obj instanceof Class<?> clazz){
        return firstInstance(clazz);
        }
        return nextInstance(obj);
        }
        catch(RuntimeException|Error ex){
        throw ex;
        }
        catch(Exception ex){
        throw new CodeGenerationException(ex);
        }
        }
```

### AnnotationAwareAspectJAutoProxyCreator#createProxy

对Bean进行AOP增强

### AbstractAutoProxyCreator#getAdvicesAndAdvisorsForBean()

获取AOP定义

### CglibAopProxy#buildProxy(ClassLoader classLoader, boolean classOnly)

Cglib AOP

创建代理对象的类型

### AbstractClassGenerator#generateClassName(Predicate nameTestPredicate)

用于生成代理对象的类名，注意，如果使用不同类加载器加载同名类且进行AOP增强时，由于生成的代理类名称相同，导致加载时会被重新加载

```java
String name=generateClassName(data.getUniqueNamePredicate());
```

String name = AbstractClassGenerator#generateClassName(data.getUniqueNamePredicate());

### AbstractClassGenerator#generate

生成代理对象类型

#### ReflectUtils.loadClass(getClassName(), classLoader);