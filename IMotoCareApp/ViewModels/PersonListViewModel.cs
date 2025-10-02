using System.Collections.ObjectModel;
using System.Windows.Input;
using IMotoCareApp.Models;
using IMotoCareApp.Services;
using Microsoft.Maui.ApplicationModel;
using Microsoft.Maui.Controls;

namespace IMotoCareApp.ViewModels;

public sealed class PersonListViewModel : BaseViewModel
{
    private readonly PersonelService _personelService;
    private string _type = string.Empty;
    private string _pageTitle = string.Empty;
    private int _range = 0;
    private bool _isRefreshing;

    public PersonListViewModel(PersonelService personelService)
    {
        _personelService = personelService;
        People = new ObservableCollection<Personell>();
        IncreaseRangeCommand = new Command(() => AdjustRange(10));
        DecreaseRangeCommand = new Command(() => AdjustRange(-10));
        RefreshCommand = new Command(async () => await LoadAsync(true));
    }

    public ObservableCollection<Personell> People { get; }

    public string PageTitle
    {
        get => _pageTitle;
        private set => SetProperty(ref _pageTitle, value);
    }

    public int Range
    {
        get => _range;
        private set
        {
            if (value < 0)
            {
                value = 0;
            }

            if (value > 1000)
            {
                value = 1000;
            }

            if (SetProperty(ref _range, value))
            {
                OnPropertyChanged(nameof(RangeDisplay));
            }
        }
    }

    public string RangeDisplay => $"Search radius: {Range} km";

    public bool IsRefreshing
    {
        get => _isRefreshing;
        set => SetProperty(ref _isRefreshing, value);
    }

    public ICommand IncreaseRangeCommand { get; }
    public ICommand DecreaseRangeCommand { get; }
    public ICommand RefreshCommand { get; }

    public void Initialize(string type, string title)
    {
        _type = type;
        PageTitle = title;
        Range = 0;
    }

    public async Task LoadAsync(bool isPullToRefresh = false)
    {
        if (string.IsNullOrWhiteSpace(_type))
        {
            return;
        }

        if (IsBusy)
        {
            return;
        }

        try
        {
            IsBusy = !isPullToRefresh;
            IsRefreshing = isPullToRefresh;

            var results = await _personelService.GetPeopleByTypeAsync(_type).ConfigureAwait(false);

            MainThread.BeginInvokeOnMainThread(() =>
            {
                People.Clear();
                foreach (var person in results)
                {
                    People.Add(person);
                }
            });
        }
        finally
        {
            IsBusy = false;
            IsRefreshing = false;
        }
    }

    private void AdjustRange(int delta)
    {
        Range += delta;
    }
}
