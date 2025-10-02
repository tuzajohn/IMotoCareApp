using System;

namespace IMotoCareApp.Helpers;

public static class ServiceHelper
{
    public static IServiceProvider? Services { get; set; }

    public static T? GetService<T>() where T : class
        => Services?.GetService(typeof(T)) as T;

    public static object? GetService(Type serviceType)
        => Services?.GetService(serviceType);
}
