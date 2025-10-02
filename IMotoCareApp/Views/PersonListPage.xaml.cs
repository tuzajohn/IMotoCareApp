using System.Linq;
using IMotoCareApp.Helpers;
using IMotoCareApp.Models;
using IMotoCareApp.ViewModels;
using Microsoft.Extensions.DependencyInjection;

namespace IMotoCareApp.Views;

public partial class PersonListPage : ContentPage
{
    private PersonListViewModel? ViewModel => BindingContext as PersonListViewModel;
    private readonly IServiceProvider _serviceProvider;
    private string _type = string.Empty;
    private string _title = string.Empty;

    public PersonListPage()
        : this(null)
    {
    }

    public PersonListPage(IServiceProvider? serviceProvider)
    {
        _serviceProvider = serviceProvider ?? ServiceHelper.Services ?? new ServiceCollection().BuildServiceProvider();
        InitializeComponent();
    }

    public void Initialize(string type, string title)
    {
        _type = type;
        _title = title;
        if (ViewModel is not null)
        {
            ViewModel.Initialize(type, title);
        }
    }

    protected override async void OnAppearing()
    {
        base.OnAppearing();
        if (ViewModel is null)
        {
            BindingContext = ActivatorUtilities.CreateInstance<PersonListViewModel>(_serviceProvider);
        }

        if (ViewModel is not null)
        {
            if (!string.IsNullOrWhiteSpace(_type) && string.IsNullOrWhiteSpace(ViewModel.PageTitle))
            {
                ViewModel.Initialize(_type, _title);
            }
            await ViewModel.LoadAsync();
        }
    }

    private async void OnSelectionChanged(object sender, SelectionChangedEventArgs e)
    {
        if (e.CurrentSelection?.FirstOrDefault() is Personell person)
        {
            if (_serviceProvider.GetService(typeof(PersonDetailPage)) is PersonDetailPage detailPage)
            {
                detailPage.Bind(person);
                await Navigation.PushAsync(detailPage);
            }
        }

        if (sender is CollectionView collectionView)
        {
            collectionView.SelectedItem = null;
        }
    }
}
