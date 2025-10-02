using System.Linq;
using System.Text.Json;
using System.Text.Json.Serialization;

namespace IMotoCareApp.Models;

public sealed class Personell
{
    [JsonPropertyName("PersonId")]
    public string PersonId { get; init; } = string.Empty;

    [JsonPropertyName("GarageId")]
    public string GarageId { get; init; } = string.Empty;

    [JsonPropertyName("Fname")]
    public string FirstName { get; init; } = string.Empty;

    [JsonPropertyName("Lname")]
    public string LastName { get; init; } = string.Empty;

    [JsonPropertyName("Contact")]
    public string Contact { get; init; } = string.Empty;

    [JsonPropertyName("Type")]
    public string Type { get; init; } = string.Empty;

    [JsonPropertyName("ImageUri")]
    public string ImageUri { get; init; } = string.Empty;

    [JsonPropertyName("GarageName")]
    public string GarageName { get; init; } = string.Empty;

    [JsonPropertyName("Address")]
    public string Address { get; init; } = string.Empty;

    [JsonPropertyName("Longitude")]
    public double Longitude { get; init; }

    [JsonPropertyName("Lattitude")]
    public double Latitude { get; init; }

    [JsonIgnore]
    public string FullName => string.Join(" ", new[] { FirstName, LastName }.Where(part => !string.IsNullOrWhiteSpace(part)));

    [JsonIgnore]
    public string DistanceSummary { get; init; } = string.Empty;

    public static Personell FromJsonElement(JsonElement element)
    {
        var model = JsonSerializer.Deserialize<Personell>(element.GetRawText()) ?? new Personell();
        return model with
        {
            DistanceSummary = element.TryGetProperty("Distance", out var distanceProperty)
                ? $"{distanceProperty.GetDouble():F1} km away"
                : string.Empty
        };
    }
}
