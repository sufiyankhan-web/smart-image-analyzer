import ScoreRing from './ScoreRing';

function ComparisonResultCard({ result }) {
  if (!result) return null;

  const winnerText = [
    `Sharpness winner: ${result.sharperImage}`,
    `Exposure winner: ${result.betterExposureImage}`,
    `Contrast winner: ${result.betterContrastImage}`,
    `Overall winner: ${result.betterOverallImage}`
  ];

  return (
    <div className="glass-card space-y-4 p-6">
      <h3 className="text-lg font-semibold text-white">Photo Comparison Tool</h3>
      <div className="grid gap-4 lg:grid-cols-2">
        <div className="rounded-xl bg-brand-panelSoft/70 p-4">
          <p className="text-sm text-brand-muted">{result.firstImage.fileName}</p>
          <div className="mt-3"><ScoreRing score={result.firstImage.finalScore} /></div>
        </div>
        <div className="rounded-xl bg-brand-panelSoft/70 p-4">
          <p className="text-sm text-brand-muted">{result.secondImage.fileName}</p>
          <div className="mt-3"><ScoreRing score={result.secondImage.finalScore} /></div>
        </div>
      </div>
      <ul className="space-y-2">
        {winnerText.map((text) => (
          <li key={text} className="rounded-xl bg-brand-panelSoft/70 px-3 py-2 text-sm text-brand-text">{text}</li>
        ))}
      </ul>
    </div>
  );
}

export default ComparisonResultCard;
