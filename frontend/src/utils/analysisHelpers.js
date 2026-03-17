export function scoreToLabel(score = 0) {
  if (score >= 85) return 'Excellent';
  if (score >= 70) return 'Great';
  if (score >= 55) return 'Good';
  if (score >= 40) return 'Fair';
  return 'Needs Improvement';
}

export function safeNumber(value, fallback = 0) {
  const parsed = Number(value);
  return Number.isFinite(parsed) ? parsed : fallback;
}

export function formatExifValue(value, empty = 'Not available') {
  return value === null || value === undefined || value === '' ? empty : value;
}
