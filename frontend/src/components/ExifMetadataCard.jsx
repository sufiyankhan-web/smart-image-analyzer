import { Camera } from 'lucide-react';
import { formatExifValue } from '../utils/analysisHelpers';

const FIELDS = [
  { key: 'cameraModel', label: 'Camera Model' },
  { key: 'iso', label: 'ISO' },
  { key: 'aperture', label: 'Aperture' },
  { key: 'shutterSpeed', label: 'Shutter Speed' },
  { key: 'focalLength', label: 'Focal Length' }
];

function ExifMetadataCard({ analysis }) {
  return (
    <div className="glass-card p-6">
      <div className="mb-5 flex items-center gap-3">
        <span className="flex h-9 w-9 shrink-0 items-center justify-center rounded-xl bg-brand-primary/10 text-brand-primary">
          <Camera className="h-5 w-5" />
        </span>
        <div>
          <h3 className="text-sm font-semibold text-white">EXIF Metadata</h3>
          <p className="text-[11px] text-brand-muted">Camera and capture settings</p>
        </div>
      </div>

      <div className="grid gap-2.5 sm:grid-cols-2">
        {FIELDS.map(({ key, label }) => (
          <div key={key} className="metric-tile">
            <p className="text-[10px] uppercase tracking-wide text-brand-muted">{label}</p>
            <p className="mt-1 text-sm font-semibold text-brand-text">{formatExifValue(analysis?.[key])}</p>
          </div>
        ))}
      </div>
    </div>
  );
}

export default ExifMetadataCard;
